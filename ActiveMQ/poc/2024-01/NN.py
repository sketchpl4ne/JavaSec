import os
import sys
import torch
import torch.nn as nn
from sklearn.metrics import confusion_matrix
from torch.utils.data import DataLoader, Dataset
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


class LoadData(Dataset):
    def __init__(self, X, y):
        self.X = X
        self.y = y

    def __len__(self):
        return len(self.X)

    def __getitem__(self, index):
        X = torch.tensor(self.X.iloc[index])
        y = torch.tensor(self.y.iloc[index])
        return X, y


# 数据归一化
def encode_numeric_range(df, names, normalized_low=0, normalized_high=1,
                         data_low=None, data_high=None):
    for name in names:
        if data_low is None:
            data_low = min(df[name])
            data_high = max(df[name])

        df[name] = ((df[name] - data_low) / (data_high - data_low)) \
                   * (normalized_high - normalized_low) + normalized_low
    return df


# 数据标准化
def encode_numeric_zscore(df, names, mean=None, sd=None):
    for name in names:
        if mean is None:
            mean = df[name].mean()

        if sd is None:
            sd = df[name].std()

        df[name] = (df[name] - mean) / sd
    return df


# 数据数值化
def Numerical_Encoding(df, label):
    labels = pd.DataFrame(label)

    label_encoder = LabelEncoder()
    enc_label = labels.apply(label_encoder.fit_transform)

    df.Label = enc_label
    return df


def preprocess(df):
    # 得到标签列索引
    all_col = df.columns

    cat_col = df.columns.drop('Label')

    # 数据标准化
    df = encode_numeric_zscore(df, cat_col)

    # 数据归一化
    df = encode_numeric_range(df, cat_col)

    # 数据数值化
    df = Numerical_Encoding(df, df.Label)

    # 异常清除
    invalid_mask = np.isnan(df) | np.isinf(df)
    valid_rows = ~np.any(invalid_mask, axis=1)
    # 仅保留有效行
    df = df[valid_rows]
    return df


class CNN(nn.Module):
    def __init__(self):
        super().__init__()
        self.backbone = nn.Sequential(
            nn.Conv1d(1, 32, kernel_size=2),
            nn.Conv1d(32, 64, kernel_size=2),
            nn.MaxPool1d(2, 2),
            nn.Conv1d(64, 64, kernel_size=2),
            nn.Conv1d(64, 128, kernel_size=2),
            nn.MaxPool1d(2, 2),
        )
        self.flatten = nn.Flatten()
        self.fc = nn.Sequential(
            nn.Linear(2304, 64),
            nn.ReLU(),
            nn.Linear(64, 64),
            nn.ReLU(),
            nn.Linear(64, y_dimension)
        )

    def forward(self, X):
        X = self.backbone(X)
        X = self.flatten(X)
        logits = self.fc(X)
        return logits


def loss_value_plot(losses, iter):
    plt.figure()
    plt.plot([i for i in range(1, iter + 1)], losses)
    plt.xlabel('Iterations (×100)')
    plt.ylabel('Loss Value')


def plotConfMatrix(conf_matrix, filename):
    # 转换成ndarray
    cm = np.array(conf_matrix)
    # 计算每个类别的总数
    class_totals = np.sum(cm, axis=1)
    # 计算百分比
    percent_matrix = (cm.T / class_totals).T * 100
    # 设置标签
    class_labels = ['BENIGN', 'Bot', 'DDoS', 'DoS GoldenEye', 'DoS Hulk', 'DoS Slowhttptest', 'DoS slowloris',
                    'FTP-Patator',
                    'Heartbleed', 'Infiltration', 'PortScan', 'SSH-Patator', 'Web Attack Brute Force',
                    'Web Attack Sql Injection',
                    'Web Attack XSS']
    # 绘制热力图
    plt.figure(figsize=(12, 12))
    heatmap = plt.imshow(percent_matrix, cmap='Blues', interpolation='nearest')
    # 添加颜色条
    cbar = plt.colorbar(heatmap, fraction=0.046, pad=0.04)
    cbar.set_ticks([0, 100])
    cbar.set_ticklabels(['0%', '100%'])
    # 添加标签和刻度
    plt.xticks(np.arange(len(class_labels)), class_labels, rotation=90)
    plt.yticks(np.arange(len(class_labels)), class_labels)
    # 添加文本标签，对角线元素超过50%的文本颜色设置为白色
    for i in range(len(class_labels)):
        for j in range(len(class_labels)):
            text_color = 'white' if percent_matrix[i, j] > 50 else 'black'
            plt.text(j, i, f'{percent_matrix[i, j]:.2f}%', ha='center', va='center', color=text_color)
    # 添加标题
    plt.title('Confusion Matrix')
    # 保存图像
    plt.savefig(filename, dpi=300)
    # 显示图形
    plt.show()


def printEvaluationMetrics(conf_matrix):
    # 根据混淆矩阵，求其他指标
    TN, FP, FN, TP = conf_matrix[0, 0], conf_matrix[0, 1], conf_matrix[1, 0], conf_matrix[1, 1]
    # 计算准确率
    accuracy = (TP + TN) / (TP + TN + FP + FN)
    # 计算召回率
    recall = TP / (TP + FN)
    # 计算精确度
    precision = TP / (TP + FP)
    # 计算 F1 值
    f1 = 2 * (precision * recall) / (precision + recall)
    # 打印结果
    print("准确率 (Accuracy): {:.4f}".format(accuracy))
    print("召回率 (Recall): {:.4f}".format(recall))
    print("精确度 (Precision): {:.4f}".format(precision))
    print("F1 值 (F1 Score): {:.4f}".format(f1))


def train(model, optimizer, loss_fn, epochs):
    losses = []
    iter = 0

    for epoch in range(epochs):
        print(f"epoch {epoch + 1}\n-----------------")
        for i, (X, y) in enumerate(train_dataloader):
            X, y = X.to(device).to(torch.float32), y.to(device).to(torch.float32)
            X = X.reshape(X.shape[0], 1, X_dimension)
            y_pred = model(X)
            loss = loss_fn(y_pred, y.long())

            optimizer.zero_grad()
            loss.backward()
            optimizer.step()

            if i % 100 == 0:
                print(f"loss: {loss.item()}\t[{(i + 1) * len(X)}/{len(train_data)}]")
                iter += 1
                losses.append(loss.item())

    return losses, iter


def test(model):
    # positive = 0
    # negative = 0
    all_labels = []
    all_predictions = []
    with torch.no_grad():
        iter = 0
        loss_sum = 0
        for X, y in test_dataloader:
            X, y = X.to(device).to(torch.float32), y.to(device).to(torch.float32)
            X = X.reshape(X.shape[0], 1, X_dimension)
            y_pred = model(X)
            loss = loss_fn(y_pred, y.long())
            loss_sum += loss.item()
            iter += 1
            all_labels.extend(y.cpu().numpy())
            all_predictions.extend(torch.argmax(y_pred, dim=1).cpu().numpy())
            # for item in zip(y_pred, y):
            #     if torch.argmax(item[0]) == item[1]:
            #         positive += 1
            #     else:
            #         negative += 1
    # acc = positive / (positive + negative)
    conf_matrix = confusion_matrix(all_labels, all_predictions)
    # acc = conf_matrix.trace() / conf_matrix.sum()
    # avg_loss = loss_sum / iter
    # print("Accuracy:", acc)
    # print("Average Loss:", avg_loss)
    print("[+] 测试完成！")
    return conf_matrix


# 1.读取数据集，拆分成训练集和测试集
df = pd.read_csv('../data/total/total_extend_5000.csv', header=None, low_memory=False)
last_column_index = df.shape[1] - 1
print(df[last_column_index].value_counts())
df.columns = [
    'Destination Port', 'Flow Duration', 'Total Fwd Packets', 'Total Backward Packets',
    'Total Length of Fwd Packets', 'Total Length of Bwd Packets', 'Fwd Packet Length Max',
    'Fwd Packet Length Min', 'Fwd Packet Length Mean', 'Fwd Packet Length Std',
    'Bwd Packet Length Max', 'Bwd Packet Length Min', 'Bwd Packet Length Mean',
    'Bwd Packet Length Std', 'Flow Bytes/s', 'Flow Packets/s', 'Flow IAT Mean',
    'Flow IAT Std', 'Flow IAT Max', 'Flow IAT Min', 'Fwd IAT Total', 'Fwd IAT Mean',
    'Fwd IAT Std', 'Fwd IAT Max', 'Fwd IAT Min', 'Bwd IAT Total', 'Bwd IAT Mean',
    'Bwd IAT Std', 'Bwd IAT Max', 'Bwd IAT Min', 'Fwd PSH Flags', 'Bwd PSH Flags',
    'Fwd URG Flags', 'Bwd URG Flags', 'Fwd Header Length', 'Bwd Header Length',
    'Fwd Packets/s', 'Bwd Packets/s', 'Min Packet Length', 'Max Packet Length',
    'Packet Length Mean', 'Packet Length Std', 'Packet Length Variance',
    'FIN Flag Count', 'SYN Flag Count', 'RST Flag Count', 'PSH Flag Count',
    'ACK Flag Count', 'URG Flag Count', 'CWE Flag Count', 'ECE Flag Count',
    'Down/Up Ratio', 'Average Packet Size', 'Avg Fwd Segment Size',
    'Avg Bwd Segment Size', 'Fwd Header Length', 'Fwd Avg Bytes/Bulk',
    'Fwd Avg Packets/Bulk', 'Fwd Avg Bulk Rate', 'Bwd Avg Bytes/Bulk',
    'Bwd Avg Packets/Bulk', 'Bwd Avg Bulk Rate', 'Subflow Fwd Packets',
    'Subflow Fwd Bytes', 'Subflow Bwd Packets', 'Subflow Bwd Bytes',
    'Init_Win_bytes_forward', 'Init_Win_bytes_backward', 'act_data_pkt_fwd',
    'min_seg_size_forward', 'Active Mean', 'Active Std', 'Active Max',
    'Active Min', 'Idle Mean', 'Idle Std', 'Idle Max', 'Idle Min', 'Label'
]
df.dropna(inplace=True, axis=0)
# 数据预处理
df = preprocess(df)
X = df.drop(columns=['Label'])
y = df['Label']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.20, random_state=50)
train_data = LoadData(X_train, y_train)
test_data = LoadData(X_test, y_test)
X_dimension = len(X_train.columns)
y_dimension = len(y_train.value_counts())
print(f"X的维度：{X_dimension}")
print(f"y的维度：{y_dimension}")

# 2.配置训练参数
batch_size = 128
epochs = 50
lr = 0.0001
print("epochs: ", epochs)
print("learning rate: ", lr)
train_dataloader = DataLoader(train_data, batch_size=batch_size)
test_dataloader = DataLoader(test_data, batch_size=batch_size)
device = 'cuda:0' if torch.cuda.is_available() else 'cpu'

# 3.加载模型
CNN_model = CNN()
CNN_model.to(device=device)
optimizer = torch.optim.SGD(CNN_model.parameters(), lr=lr)  # 优化器
loss_fn = nn.CrossEntropyLoss()  # 损失函数

# 4.训练模型，预测结果
if os.path.exists('../output/CNN/epoch_50/CNN_model.pth'):
    CNN_model.load_state_dict(torch.load('../output/CNN/epoch_50/CNN_model.pth'))
else:
    losses, iter = train(CNN_model, optimizer, loss_fn, epochs)
    torch.save(CNN_model.state_dict(), '../output/CNN/epoch_50/CNN_model.pth')

    loss_value_plot(losses, iter)
    plt.savefig('../output/CNN/epoch_50/loss.jpg')
    plt.show()
conf_matrix = test(CNN_model)
plotConfMatrix(conf_matrix, '../output/CNN/epoch_50/confusion_matrix.png')
printEvaluationMetrics(conf_matrix)

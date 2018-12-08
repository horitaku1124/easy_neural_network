from sklearn import datasets

iris_data = datasets.load_iris()
input_data = iris_data.data
correct = iris_data.target

print ("a0,a1,a2,a3,,label")
for i in range(len(input_data)):
    target = input_data[i]

    putStr = ','.join(str(i) for i in target)
    putStr += ',,' + str(correct[i])
    print (putStr)
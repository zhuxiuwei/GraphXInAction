val trainSet = augment(trainG)
val model = LogisticRegressionWithSGD.train(trainSet, 10)

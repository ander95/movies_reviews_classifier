# $1 = weka.jar-aren path-a
# $2 = dev.arff-aren path-a
# $3 = train.arff-aren path-a
# $4 = blind_test.arff-aren path-a
# $5 = hemuga path-a

java -cp $1 weka.core.Instances append $2 $3 > /tmp/a.arff
java -cp $1 weka.core.Instances append /tmp/a.arff $4 > $5

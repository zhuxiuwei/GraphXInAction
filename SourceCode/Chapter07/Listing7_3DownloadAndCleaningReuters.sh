wget https://archive.ics.uci.edu/ml/machine-learning-databases/reuters21578-mld/reuters21578.tar.gz
tar -xzvf reuters21578.tar.gz reut2-000.sgm
cat reut2-000.sgm | tr '\n' ' ' | sed -e 's/<\/BODY>/\n/g' | sed -e 's/^.*<BODY>//' | tr -cd '[[:alpha:]] \n' >rcorpus

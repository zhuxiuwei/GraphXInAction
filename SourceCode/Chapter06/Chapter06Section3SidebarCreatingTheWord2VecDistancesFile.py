from gensim.models import Word2Vec 
model = Word2Vec.load_word2vec_format( 
    'GoogleNews-vectors-negative300.bin',  
    binary=True) 
f = open('animal_terms.txt') 
animals = f.read().splitlines() 
animals = [x.lower() for x in animals if x.lower() in model.vocab.keys()] 
f.close() 
f = open('animal_distances.txt','w') 
f.truncate() 
for i in range(0, len(animals)): 
  for j in range(i, len(animals)): 
    f.write('%s,%s,%1.6f\n' %  
        (animals[i], animals[j],  
        1 - model.similarity(animals[i], animals[j]))) 
f.flush() 
f.close() 

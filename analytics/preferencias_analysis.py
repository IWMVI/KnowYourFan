import pandas as pd

df = pd.read_csv("http://localhost:8080/api/analytics/exportar-csv")

df['intereses'] = df['intereses'].fillna('').apply(lambda x: [i.strip().lower() for i in x.plit(',')])

exploded = df.explode('intereses')

interesses_counts = exploded['interesses'].value_counts()
print(interesses_counts)
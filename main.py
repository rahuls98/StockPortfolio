import mysql.connector

# username = input("Enter username: ")
# password = input("Enter password: ")
username = "root"
password = "Metrolife39#"

db = mysql.connector.connect(
  host="localhost",
  database="sharkdb",
  user=username,
  password=password
)

cursor = db.cursor()

cursor.execute('''
    SELECT state, town 
    FROM township 
    GROUP BY town, state 
    ORDER BY state, town
''')
towns_states = cursor.fetchall()
 
towns_states = [f'{town_state[0]}, {town_state[1]}' for town_state in towns_states]
for ind, town_state in enumerate(towns_states):
    print(f'{ind + 1}) {town_state}')

town_state = None
while (True):
    town_state = input("Enter town number: ")
    if town_state.isnumeric():
        town_state = int(town_state)
        if town_state > 0 and town_state <= len(towns_states):
            break
state,town = towns_states[town_state - 1].split(", ")
print(state)
print(town)

cursor.execute(f'CALL allReceivers("{town}", "{state}")')
receivers = cursor.fetchall()

for receiver in receivers:
    print(receiver)

db.close()
# Chess Sheriff
Quickly find helpful statistics to determine if a chess.com user is cheating! Deployed on AWS [here](http://ec2-54-237-9-177.compute-1.amazonaws.com:3000/).

# Demo
https://user-images.githubusercontent.com/44343021/167313438-2119aaee-ba94-4d83-80e4-ea4e16091fbc.mp4

# How it works
Win rates, CAPS scores, and time taken to make a move are all useful statistics to understand how someone plays chess. By simply searching for a chess.com user and a date range, we can find all these statistics quickly.

# What is a CAPS score?
CAPS is a score (out of 100) used to measure a player's performance in a game. 

# General Red Flags
* High win rate over many games and a low rating.
* High Average CAPS Score (higher than 85).
* Relatively high Lowest CAPS Score (higher than 60) over many games .
* Extremely consistent average move time (cheaters tend to spend the same amount of time on every move).

# Running Locally
With Docker installed, run `docker compose up` to access on port 8080.

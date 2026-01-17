# AstroDash 
## Project Description
AstroDash is a cross-platform Java desktop 2D platformer game inspired by Super Mario Bros, featuring fast-paced gameplay, dynamic level loading, and a cloud-hosted global leaderboard. My project combines game development, object-oriented design, and full-stack backend engineering. I utilized strong object-oriented principles through inheritance, encapsulation, unit testing, and UML-based class design. I also applied GRASP principles: the Controller pattern is used in the Game and UI classes to delegate requests, while the Creator pattern is applied in the Map and game object classes to manage object creation and maintain clear responsibilities. Map and object data are dynamically read from external files, supporting scalable level design.


## Features
- Cross-platform Java desktop game (Windows & macOS)
- Global cloud leaderboard for coins and fastest times (AWS Elastic Beanstalk + Dynamo DB)
- Smooth player movement, jumping, and collision detection
- Coin collection, scoring, and speedrun timing
- Dynamic enemies and lava pits affecting player health
- Fireball mechanics for gameplay interaction
- Modular game architecture using OOP & GRASP principles


## Cloud Leaderboard System
AstroDash includes a production-style backend that supports real-time score submission and retrieval:
- Spring Boot REST API for leaderboard operations
- AWS DynamoDB for persistent leaderboard storage
- AWS Elastic Beanstalk for backend deployment
- REST endpoints for submitting and fetching top scores


## Technical Architecture
- Frontend (Desktop Client):
    - Java
    - Processing (PApplet) libraries for rendering and game loop
- Backend (Cloud):
    - Java + Spring Boot
    - AWS DynamoDB 
    - AWS Elastic Beanstalk


## How to Run
1. Download the latest AstroDash desktop release (Windows or macOS)
2. Follow the instructions on the release info according to your operating system
3. Launch the game and save Astro!


## Storyline
Mission Briefing: Your loyal dog Astro has been kidnapped by space enemies and has been taken to their planet. You must speed-run your way through four dangerous maps leaping over lava, dodging enemies, and collecting coins to boost your score. At the end, you will face a showdown with the final boss.
(Inspired by SuperMario Bros and Fireboy & Watergirl)


## UML Diagrams 
<img width="271" height="294" alt="Screenshot 2025-12-06 at 8 04 40 PM" src="https://github.com/user-attachments/assets/3d4a5dca-d6c1-4d4f-a0c4-e8d9ab24455f" />
<img width="415" height="232" alt="Screenshot 2025-12-06 at 8 04 53 PM" src="https://github.com/user-attachments/assets/8546beb7-c019-4f4e-aa2e-81dd71de80f6" />

## Gameplay Screenshots + Recording
- Startup Screen
<img width="226" height="219" alt="Screenshot 2025-12-06 at 8 08 17 PM" src="https://github.com/user-attachments/assets/d0e65212-2930-4de2-affa-063f6a4e3fe4" />

- First Map Design
<img width="226" height="219" alt="Screenshot 2025-12-06 at 8 08 30 PM" src="https://github.com/user-attachments/assets/83719b8b-33cd-4f1b-b413-0cdf0cd420a6" />

- Final Boss Battle
<img width="226" height="219" alt="Screenshot 2025-12-06 at 8 08 42 PM" src="https://github.com/user-attachments/assets/53fbc523-1101-4cb9-a7df-d93711feb858" />

- Game Play!
  [https://www.youtube.com/watch?v=CtLrwTuRX74]










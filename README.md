# Overview

This project is a game of goose I did during my master's degree after a few hours of Java SE course. It was our final exam in Java and we had to conceive and code the game. The rule was to make the game from scratch to see if we understood how to use classes and how to code simple logic in Java.

A console interface that displays the game results would have been enough for our professor but I wanted to push the subject further to see what I could actually do with Swing and AWT. Furthermore, I wanted to prepare myself for my future internship during which I knew I would have to use Java to create an application with an interface.

# Table of Contents
- [Overview](#overview)
- [Table of Contents](#table-of-contents)
- [I - Game rules](#i---game-rules)

# I - Game rules

The game of goose is a board game that is played with dice. All the players start at the first space and the goal is to reach the last space before the others. The first player reaching the last space wins.

Players take turns to roll the dice and advance as many spaces as the result of the dice.

In my game, I've implemented special spaces that give bonuses or penalties :
- the player passes his/her turn (apply on the next turn)
- the player advances of 1 or 2 spaces
- the player moves back of 1 or 2 spaces
- the player teleports to a specific space (it can be closer to the end or further)

 

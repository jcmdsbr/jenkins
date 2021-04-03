# Building a jenkins docker image as a code :sunglasses:

## Give a Star! :star:

If you liked the project, please give a star ;)

## You need some of the fallowing tools :exclamation:

- Visual Studio Code or another editor
- .Net Core 3.1

## Configuration Steps 🏗️

- Create a plugins.txt file to configure a essentials plugins from a DevOp's pipelines.

- Create groovy scripts to generate a base configuration from jenkins
	- Create a base admin user/password
	- Create a base globals variables

## Build Steps ✔️

Change version in docker compose file

- To Run and Create:

```sh
    - docker-compose -f "docker-compose.yml" up -d --build  --no-cache
```
 - To Stop:
 
```sh 
    - docker-compose -f "docker-compose.yml" down
```

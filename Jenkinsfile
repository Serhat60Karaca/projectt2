pipeline {
    agent any
    tools {
        gradle 8.7
    }

    environment {
        DOCKER_HUB_CREDENTIALS = credentials('DockerAuth')
        DOCKER_IMAGE = "serhatkaraca60/projectt2"
        GIT_REPO = "https://github.com/Serhat60Karaca/projectt2"
    }

    stages {
        stage('Pull the project form GitHub') {
            steps {
                echo 'Getting the project from GitHub'
                git url: "${env.GIT_REPO}", branch: 'master'
            }
        }

        stage('Building the jar file') {
            steps {
                echo 'Start building the jar'
                sh 'gradle clean bootJar'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${env.DOCKER_IMAGE}:latest")
                }
                echo 'Image has been built'
            }
        }

        stage('Login to DockerHub') {
            steps{
                withCredentials([usernamePassword(credentialsId: 'DockerAuth', passwordVariable: 'DockerAuthPassword', usernameVariable: 'DockerAuthUser')]) {
                    sh "docker login -u ${env.DockerAuthUser} -p ${env.DockerAuthPassword}"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push serhatkaraca60/projectt2:latest'
                echo 'The image is pushed'
            }
        }


    }
}
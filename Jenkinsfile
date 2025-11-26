pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
    stages {
         stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ArfHassen/app-spring-demo.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -s settings.xml clean package'
            }
        }
        stage('DEBUG BRANCH') {
          steps {
            echo "BRANCH_NAME = '${env.BRANCH_NAME}'"
            echo "GIT_BRANCH   = '${env.GIT_BRANCH}'"
          }
        }
        stage('Deploy SNAPSHOT') {
            when {
                expression { return env.GIT_BRANCH == 'origin/main' }
            }
            steps {
                sh 'mvn -s settings.xml deploy'
            }
        }
        stage('Configure Git') {
                    steps {
                        withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-ssh-key', keyFileVariable: 'SSH_KEY')]) {
                            sh """
                                mkdir -p ~/.ssh
                                eval \$(ssh-agent)
                                ssh-add $SSH_KEY
                                ssh-keyscan github.com >> ~/.ssh/known_hosts

                                git config user.name "ArfHassen"
                                git config user.email "arf.hassen@gmail.com"
                            """
                        }
                    }
        }
        stage('Release') {
            when {
                expression { return env.GIT_BRANCH == "origin/main" }
            }
            steps {
                sh "mvn -s settings.xml release:prepare release:perform -DskipTests"
            }
            /* steps {
                sh """
                    mvn versions:set -DremoveSnapshot
                    mvn -s settings.xml clean deploy -P release
                    mvn versions:commit
                """
            } */
        }
    }
    post {
            always {
                echo 'Build terminé.'
            }
            success {
                echo 'Build réussi !'
            }
            failure {
                echo 'Build échoué.'
            }
        }
}

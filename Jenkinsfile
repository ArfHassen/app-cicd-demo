pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    environment {
        MAVEN_SETTINGS = 'settings.xml' // ton fichier settings Maven
        GIT_USER = 'ArfHassen'
        GIT_EMAIL = 'arf.hassen@gmail.com'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'git@github.com:ArfHassen/app-cicd-demo.git'
            }
        }

        stage('Configure SSH for GitHub') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-ssh-key', keyFileVariable: 'SSH_KEY')]) {
                    sh """
                        mkdir -p ~/.ssh
                        eval \$(ssh-agent -s)
                        ssh-add \$SSH_KEY
                        ssh-keyscan github.com >> ~/.ssh/known_hosts
                        echo "Host github.com
HostName github.com
User git
IdentityFile \$SSH_KEY
IdentitiesOnly yes" > ~/.ssh/config
                        chmod 600 ~/.ssh/config
                        git config user.name "${GIT_USER}"
                        git config user.email "${GIT_EMAIL}"
                    """
                }
            }
        }

        stage('Build & Test') {
            steps {
                sh "mvn -s ${MAVEN_SETTINGS} clean verify"
            }
        }

        stage('Deploy SNAPSHOT') {
            when {
                expression {
                    sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim() == 'main'
                }
            }
            steps {
                sh "mvn -s ${MAVEN_SETTINGS} deploy"
            }
        }

        stage('Release') {
            when {
                expression {
                    sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim() == 'main'
                }
            }
            steps {
                sh """
                    mvn -B -s ${MAVEN_SETTINGS} release:clean release:prepare release:perform \
                        -Darguments="-DskipTests"
                """
            }
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

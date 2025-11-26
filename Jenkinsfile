pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    environment {
        MAVEN_SETTINGS = 'settings.xml'
        GIT_USER = 'ArfHassen'
        GIT_EMAIL = 'arf.hassen@gmail.com'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ArfHassen/app-cicd-demo.git', credentialsId: 'github-username-token'
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
                    def branch = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    echo "Current branch = ${branch}"
                    return branch == 'main'
                }
            }
            steps {
                sh """
                    # Config Git
                    git config user.name "${GIT_USER}"
                    git config user.email "${GIT_EMAIL}"

                    # Test SSH (ignore le code de retour)
                    ssh -T git@github.com || true

                    # Maven Release Plugin
                    mvn -B -s ${MAVEN_SETTINGS} release:clean release:prepare release:perform -Darguments="-DskipTests"
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


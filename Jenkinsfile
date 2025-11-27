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
        SSH_KEY_FILE = '/var/jenkins_home/.ssh/id_ed25519'
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
                    // Déploiement SNAPSHOT uniquement sur branches main/develop
                    def branch = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    return branch == 'main' || branch == 'develop'
                }
            }
            steps {
                sh "mvn -s ${MAVEN_SETTINGS} deploy"
            }
        }

        /* stage('Release') {
            when {
                // Release uniquement si un tag correspond à vX.Y.Z
                tag "v*.*.*"
            }
            steps {
                sh """
                    git config user.name "${GIT_USER}"
                    git config user.email "${GIT_EMAIL}"

                    # Test SSH
                    ssh -T git@github.com || true

                    # Maven Release Plugin : prepare & perform
                    mvn -B -s ${MAVEN_SETTINGS} release:clean release:prepare release:perform -Darguments="-DskipTests"
                """
            }
        } */
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

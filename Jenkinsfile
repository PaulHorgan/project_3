pipeline {
    agent any
    environment {
        NODE_HOME = '/usr/local/bin/node' //
        MAVEN_HOME = '/usr/local/bin/mvn' // Adjust to your maven path
//         DEPLOY_USER = credentials('DEPLOY_CREDENTIALS_USER') // Jenkins credentials for SSH
//         DEPLOY_PASSWORD = credentials('DEPLOY_CREDENTIALS_PASSWORD')
        MYSQL_HOST = "localhost"
        MYSQL_USER = 'root'
        MYSQL_PASSWORD = 'root'

    }
    stages {
        // Front end build and deployment using PM2 to run persistently
        stage('Build and Run Front End with PM2') {
            steps {
                dir('front end/LegacyFrontEnd') {
                    echo 'Building and deploying Front End (React app)'
                    bat '''
                    npm install
                    npm run build
                    pm2 delete LegacyFrontEnd || true
                    pm2 start npm --name "LegacyFrontEnd" -- start
                    '''
                    // The Front end React app is started using PM2, so it keeps running even after the pipeline completes.
                }
            }
        }

        // Back end: Build and deploy the Items service, run persistently using PM2
        stage('Build and Run Items Service with PM2') {
            steps {
                dir('back end/LegacyCodeItems') {
                    echo 'Building Items service using Maven'
                    bat '''
                    mvn clean install
                    pm2 delete LegacyCodeItems || true
                    pm2 start java --name "LegacyCodeItems" -- -jar target/LegacyCodeItems-app.jar
                    '''

                }
            }
        }

        // Back end: Build and deploy the Cart service, run persistently using PM2
        stage('Build and Run Cart Service with PM2') {
            steps {
                dir('back-end/LegacyCodeCart') {
                    echo 'Building Cart service using Maven'
                    bat '''
                    mvn clean install
                    pm2 delete LegacyCodeCart || true
                    pm2 start java --name "LegacyCodeCart" -- -jar target/LegacyCodeCart-app.jar
                    '''
                }
            }
        }

        // Back end: Build and deploy the Payments service, run persistently using PM2
        stage('Build and Run Payments Service with PM2') {
            steps {
                dir('back-end/stripe-payment') {
                    echo 'Building Payments service using Maven'
                    bat '''
                    mvn clean install
                    pm2 delete stripe-payment || true
                    pm2 start java --name "stripe-payment" -- -jar target/stripe-payment-app.jar
                    '''
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
        failure {
            echo 'Build failed. Check the logs.'
        }
    }
}

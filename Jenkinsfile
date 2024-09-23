pipeline {
  agent any
  stages {
  stage('Build Front End') {
    steps {
        dir('front end'){
            bat '''
            npm install
            npm run build
            '''
            echo 'Deploying Front End'
            //
        }
    }
  }
  stage ('Build Stockroom')
    steps{
        dir('back end/LegacyCodeItems') { // Navigate to items directory
            echo 'Installing Items Dependencies'
            bat '''
            mvn clean install
            '''
            echo 'Deploying Items'
        }
    }
  }
  stage('build Cart')
    steps {
        dir('back end/LegacyCodeCart') { // Navigate to Cart directory
            echo 'Installing Cart Dependencies'
            bat '''
            mvn clean install
            '''
            echo 'Deploying CArt'
        }
    }
  }
  stage ('build payments (stripe)') {
    steps {
        dir('back end/stripe-payment') { //Navigate to Stripe location
            echo 'building Stripe payment interface'
            bat '''
            mvn clean install
            '''
            }
        }
  }
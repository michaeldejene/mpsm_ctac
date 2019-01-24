stage 'build_Project'
node{
echo 'Building..'
  if(isUnix()){
  echo 'second Building..'
  sh './gradlew clean assemble'

  }
  else{
    bat 'gradle build --info'
  }
}
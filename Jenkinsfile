stage 'build_Project'
node{
echo 'Building..'
  if(isUnix()){
  echo 'second Building..'
  sh 'gradle build --info'

  }
  else{
    bat 'gradle build --info'
  }
}
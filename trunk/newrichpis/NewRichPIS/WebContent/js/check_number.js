function checkNumber(keyValue)
{
  var dataCount=0;

    for(i=0 ; i<keyValue.length ; i++){
         if(keyValue.charAt(i)<'0' || keyValue.charAt(i)>'9'){
           dataCount +=1;
           break ;
         }
   }

  if (dataCount >0){
    return 1 ;
  } else {
    return 0 ;
  }
}


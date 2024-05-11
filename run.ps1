# Get the current date and time
$dateTime = Get-Date

# Format the date and time components
$day = $dateTime.Day.ToString("00")
$month = $dateTime.Month.ToString("00")
$year = $dateTime.Year.ToString("00")
$hour = $dateTime.Hour.ToString("00")
$minute = $dateTime.Minute.ToString("00")
$second = $dateTime.Second.ToString("00")

#previous log cleanup
Compress-Archive -update docker-service*.log docker-service.log.zip
Remove-Item docker-service*.log

# Construct the log file name
$logFile = "docker-service_${day}${month}${year}_${hour}${minute}${second}.log"
# docker system prune --all --force --volumes > ${logFile} 2>&1
docker-compose up --build > ${logFile} 2>&1

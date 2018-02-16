# projectmercury

Aplicación construida con Spring Boot. Ofrece un servicio de localizador de rutas aéreas y horarios, para viajes directos o con una escala, explotando servicios de test de ryanair.

El contex-root = "/mercury/" los parametros de entrada son:
  departure - a departure airport IATA code
  departureDateTime - a departure datetime in the departure airport timezone in ISO format
  arrival - an arrival airport IATA code
  arrivalDateTime - an arrival datetime in the arrival airport timezone in ISO format
  
Ejemplo de la llamada: 
http://localhost:8080/mercury/interconnections?departure=lis&arrival=Lux&departureDateTime=2018-03-10T15:00&arrivalDateTime=2018-03-12T20:00

informacion:
El servicio muestra escalas de mas de 2 horas entre vuelos y como máximo de 8 horas de diferencia.

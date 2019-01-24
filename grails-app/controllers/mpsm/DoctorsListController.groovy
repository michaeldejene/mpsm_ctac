package mpsm

import grails.artefact.controller.RestResponder
import org.grails.core.artefact.*

import mpsm.api.ApiResponse

class DoctorsListController {

    def index()


     {
         render(contentType: "text/json") {
             people {
                 person(firstName:'John', lastName:'Doe')
                 person(firstName:'Jane', lastName:'Williams')
             }
         }

         System.out.println("HELLOoooo")
     }

    def fromDb() {

        def results = Doctors.list()
        System.out.println(results[0].firstName)

        render(contentType: 'text/json') {
            Doctors {

                Doctor(firstName:results[0].lastName, lastName:results[0].firstName)

            }
        }


    }





    def responds() {

        def results = Doctors.list()
        System.out.println("HEY" + results[0].firstName)

        respond ApiResponse.get200Response([Doctors.list()]).autoFill(params)
       return
        }

    def show() {

        def results = Doctors.list()
        System.out.println("HEY" + results[0].firstName)

        respond ApiResponse.get200Response([Doctors.list()]).autoFill(params)
        return

        //response.status = 400
        //respond ApiResponse.get400Response("Invalid 'data' parameter value, valid values are: which can have multiple values seperated by commas.")
        //return
    }


}

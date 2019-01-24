package mpsm

import mpsm.api.ApiResponse
import mpsm.api.doctors.DoctorDetails

class DoctorsController {

    def index() {

        def doctors = Doctors.list()
        [docs:doctors]
    }
    def issues() {

        response.status = 400
        respond ApiResponse.get400Response("Invalid 'data' parameter value, valid values are: which can have multiple values seperated by commas."),view:'issues'
        return
    }

    def getDocDetails(Doctors doc){
        DoctorDetails dd =  new DoctorDetails(doctors : doc)
        dd

    }

    def show() {
        def results = Doctors.executeQuery("select distinct firstName, lastName from Doctors")
        System.out.println("HEY" + results)

        def doctors = Doctors.createCriteria().list(){
            'eq' 'active' , true
        }

        def doctorsDetailList = []

        doctors.each {Doctors doc ->
            DoctorDetails details = getDocDetails(doc)
            if(details){
                doctorsDetailList << details
            }

        }

        println('here' + doctorsDetailList)



        respond ApiResponse.get200Response(doctors).autoFill(params)
        //return
    }
}

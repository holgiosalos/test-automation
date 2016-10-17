Feature: Schedule Medical Appointments
  The Doctor and Patient should be registered before the scheduling of the medical appointment
  Date of medical appointment should be greater than current date

  Background:
    Given I am logged in the Home Page
      And the following patients are requesting a medical appointment
      | name | last_name | telephone | identification_type  | identification | prepaid |
      | luis | perez     | 3455676   | Cédula de ciudadanía | 29876453       | true    |
      | lida | espinosa  | 2545874   | Pasaportes           | 54789654       | false   |
      And the following doctors are available to attend patients
      | name | last_name | telephone | identification_type | identification |
      | pepe | arboleda  | 3745898   | Cédula extranjería  | 47895654       |
      | lina | ocampo    | 2456576   | Pasaportes          | 32456432       |

    Scenario: Scheduling of a Medical for registered Patient and Doctor, and correct date
      When I open the Appointment Scheduling option
       And I choose the Appointment Date to be "10/20/2016"
       And I set the Patient document to be "29876453"
       And I set the Doctor document to be "47895654"
       And I add "Patient has prepaid medicine" as Appointment note
       And I save the Appointment
      Then I expect the Appointment to be scheduled

    Scenario: Scheduling of a Medical Appointment when Patient is not registered
      When I open the Appointment Scheduling option
       And I choose the Appointment Date to be "10/20/2016"
       And I set the Patient document to be "999"
       And I set the Doctor document to be "47895654"
       And I save the Appointment
      Then I expect the Appointment to not be scheduled
       And I expect the Error message to say document of patient is not registered

    Scenario: Scheduling of a Medical Appointment when Doctor is not registered
      When I open the Appointment Scheduling option
       And I choose the Appointment Date to be "10/20/2016"
       And I set the Patient document to be "29876453"
       And I set the Doctor document to be "999"
       And I save the Appointment
      Then I expect the Appointment to not be scheduled
       And I expect the Error message to say document of doctor is not registered

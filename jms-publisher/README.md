#Reliable Messaging with BPMN

BPS workflow engine provides the capability to extend the existing task implementations to support any custom requirements in an organization. The example illustrated here would reflect the following,

 1. Writing a custom task using BPMN
 2. Connecting with the JMS provider to reliably deliver a message to a service 
 3. Integrating the WSO2 BPS with WSO2 MB
 4. End to end workflow on how a message could be delivered reliably from a service orchestration engine to WSO2 ESB. 
 
#Why do we need reliability in workflows ?  

There could be several ways to achieve guaranteed delivery in a workflow. Some may argue workflows necessarily allows to retry upon failure to interact with other systems. While that argument is true. The counter argument would be what would an organization or an individual gain or lose by having a workflow defined to retry as a remedy for fault resilience. 

Gains 

 1. Constant reminders/retries would ensure to retry upon failure, ensuring guaranteed delivery 
 
Limitations 
 
 1. Constant retries would make the calling party to use its processing power to periodically poll until the message goes through 
 2. This will flood the network traffic 
 3. When human interaction is involved and if it involves an approval process, the person will have to iteratively keep approving the records until the connecting systems are available during the failure. 
 
 Hence, this sample intends to illustrate how fault resilience could be obtained overcoming the limitations mentioned above.  
 
#Scenario 

The sample defines a workflow which will be triggered upon sending registration information with regards to a user. 
Initially there will be a process which requires the manager/admin to approve the registration. Upon approval the manager specifies which department the registration should be notified. Each department would be represented through a queue which will be created dynamically with the broker. In this sample, ESB would act as a JMS consumer, which will consume messages from queues in the broker (‘sales’ and ‘marketing’). Once the message is consumed by the ESB, it will log the message which was consumed as a result. 

![Deployment] (images/deployment-overview.jpg)
 
 The BPS workflow looks like the following,
 
![Work-Flow] (images/work-flow.jpeg)
 
#Setting up

For this example WSO2 MB is used as the message broker and WSO2 ESB is used as the enterprise service bus

1. Locate $WSO2_BPS_HOME/repository/components/lib directory and copy the ReliableMessageFlow.jar
2. Once BPS is started the corresponding *.bar file could be found in ReliableMessageFlow.bar. Upload the file into BPS
3. Locate the $WSO2_ESB_HOME/repository/deployment/server/synapse-configs/default directory and replace the existing ‘proxy-  services’ with the proxy-services
4. Copy JMS libs and configure both MB and ESB
5. Once the above steps are completed, start an instance of WSO2 BPS, WSO2 MB and WSO2 ESB. When running locally each of these instances could be started by setting the offset. For the depicted scenario to work the offset of each of the products should be the following, 

           WSO2 BPS Offset = 1
           WSO2 ESB Offset = 2
           WSO2 MB Offset = 0 (Default)
           
#Execution 

Once the setup is running. In order to run the sample follow the steps below,

1. Log into the BPMN explorer (https://bpshost:9444/bpmn-explorer/process) and find and execute the process ‘RelaibleMessageFlow’  
2. Once the process is started a form will be prompted to enter registration information. Fill in the values by giving an appropriate username and registration number and click ‘start’ to submit the process. 

 ![Exec-Step-1] (images/img-1.png) 
 
3. Navigate to ‘Tasks’ and click on ‘MyTasks’. The task to approve the message would be indicated. Click on the task and the following form will be displayed.  
 
 ![Exec-Step-2] (images/img-2.png)
 
4. Under Approval chose ‘Approve’ to continue the flow. If ‘Disapprove’ was selected the flow will end. Under department chose where the request needs to be routed. For each department a queue will be created in the broker. Once the department is selected click ‘Complete Task’ to finalize.  
 
#Observation 

Once approved the ESB will consume and log the message on it’s console.

![Exec-Step-3] (images/img-3.png)

#Alternate Flows

Follow the steps mentioned in the execution section above. Instead this time ensure that the ESB is shutdown when the message is approved. 

1. Once approved navigate to the message broker console (https://brokerhost:9443/carbon/queues/queue_details.jsp?region=region1&item=queue_list) and browse for queues. Since the ESB is shut the message would appear in the queue 
![Exec-Step-4] (images/img-3.5.png)
2. Browse the message which is in the queue and the content could be viewed for verification 
![Exec-Step-5] (images/img-4.png)
3. Once the verification is complete. Start the ESB. Once the ESB is started you would notice the message which was in the queue was consumed and ESB logs the message
 

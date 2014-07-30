Jaws
====

Jaws is a simple Wicket application which talks to [Trim](http://www.github.com/alex-moon/trim) and [Amazon DynamoDB](http://aws.amazon.com/dynamodb/) to generate and watch correlated terms in texts.

Setup
-----

Pull the repo::

    git clone git@github.com:alex-moon/jaws

Build::

    mvn package

Add a Tomcat entry to ``/var/lib/tomcat7/conf/Catalina/localhost/jaws.xml``::

    <Context path="/jaws" docBase="/opt/play/jaws-0.6.9.war" debug="2" privileged="true" allowLinking="true" crossContext="true" />

And restart Tomcat::

    service tomcat7 restart

Congrats! You now have Jaws running on ``http://localhost:8080/jaws/`` - default username and password are::

    admin
    fmY7umcnXN1IxF0lt1wH

Override these by adding the following to your ``JAVA_OPTS`` in ``/etc/default/tomcat7``::

    -Djaws.password=yoursupersecretpassword -Djaws.username=yourcoolusername

DynamoDB
--------

Jaws also expects a DynamoDB table to be in place. By default this table is called "Jaws" but you can override this in your ``JAVA_OPTS`` too::

    -Djaws.tablename=MyOwnPersonalJawsOrWhatHaveYou

On AWS Jaws can talk to DynamoDB by assigning the relevant policy to the EC2 instance's role (more in the next section). On local, however, you'll have to provide these yourself.
Create the following file at /usr/share/.aws/credentials::

    [default]
    aws_access_key_id = YOURKEYIDHERE
    aws_secret_access_key = YOURSECRETHERE

You'll get these details from Amazon.

Amazon AWS
----------

First thing you need to do is go to your [AWS Management Console](https://console.aws.amazon.com/). Go to the [IAM](http://aws.amazon.com/iam/) dashboard and create a new user.
When your user has been created Amazon will ask you if you want to generate a key-secret pair for this user - accept this and take note of both values (these are your ``credentials`` - see section above).

Once the user has been created select it in the **Users** list and hit the **Permissions** tab. Scroll down to find the **Attach User Policy** button and click it. You will want to add both **Amazon DynamoDB Full Access** and **AWS Elastic Beanstalk Full Access** to the user (or you can make these permissions more fine-grained if you desire).

Now create your ``credentials`` file (as above) with the key-secret pair you obtained for the user you've created and your local instance will be able to talk to DynamoDB and Elastic Beanstalk.

Second thing you need to is create your **DynamoDB** table (in the **Management Console**). Create a single Hash key called **Term** (which is a **String** value) and set your Read and Write Capacity values as you wish (10 and 5 respectively are available to the free tier). Update your ``jaws.tablename`` in your ``JAVA_OPTS`` as above and your local instance will be able to access the database straight away.

Finally you need to go to the [Elastic Beanstalk](http://aws.amazon.com/elasticbeanstalk/) dashboard and create a new Application with the following values:

|       |       |
| ----- | ----- |
| Environment Tier | Web Server |
| Configuration | Tomcat |
| Environment Type | Single instance |

And upload your compiled WAR file (at ``/opt/play/`` if you haven't changed your ``target_dir`` in ``pom.xml``).

Once your environment is ready, don't forget to set your ``JAVA_OPTS`` appropriately in the **Elastic Beanstalk** dashboard:

1. Go to **Configuration**
2. Go to **Software Configuration**
3. Scroll down to **Environment Properties** and add them to the list (no ``-D`` needed).

These will take some minutes to take effect. Once they do, your Jaws instance is alive and kicking!

Once you've got Jaws running, you next need to set up Trim. Go to https://www.github.com/alex-moon/trim/ for details.


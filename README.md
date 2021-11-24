<h1>lecture-17</h1>

<p>Home task lecture 17:</p>
<ol>
<li>Git repo</li>
<li>READMEnj file with rules from lecture</li>
<li>Create new maven / gradle project</li>
<li>Create two pojo classes with 1 to 1 relations</li>
<li>Create DB schema for classes from #4</li>
<li>Add JPA + hibernate libs to project</li>
<li>Configure POJO mapping with JPA annotations</li>
<li>Create DAO (use EntityManager) for POJOs</li>
<li>Tests</li>
</ol>


<h2>What's done:</h2>
<ol>
<li>Created two pojo classes(Teacher, TeacherDetails) with OneToOne relations</li>
<li>Created DB schema for classes</li>
<li>Added JPA libs to project. Hibernate provider uses.</li>
<li>Configured POJO mapping with JPA annotations</li>
<li>Created CRUD DAO</li>
<li>Added tests for DAO (for TeacherDetailsDAO full)</li>
<li>Added Exceptions support for DAO</li>
</ol>

<h3>To run tests and App you should:</h3>
<ol>
<li>Build project to run tests: $mvn clean package</li>
<li>Run new mysql server for the App: $docker-compose up -d</li>
<li>Run liquibase to create tables and insert data: $mvn liquibase:update</li>
<li>Run App</li>
</ol>
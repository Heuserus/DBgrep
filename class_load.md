# How to ClassLoader in Java


## Allgemeine Methode zum Laden

Code von [Stack  Overflow](https://stackoverflow.com/questions/6219829/method-to-dynamically-load-java-class-files)

```java
// Create a File object on the root of the directory containing the class file
File file = new File("c:\\myclasses\\");

try {
    // Convert File to a URL
    URL url = file.toURI().toURL();          // file:/c:/myclasses/
    URL[] urls = new URL[]{url};

    // Create a new class loader with the directory
    ClassLoader cl = new URLClassLoader(urls);

    // Load in the class; MyClass.class should be located in
    // the directory file:/c:/myclasses/com/mycompany
    Class cls = cl.loadClass("com.mycompany.MyClass");
} catch (MalformedURLException e) {
} catch (ClassNotFoundException e) {
}

// try catch?
cl.close()
```

Methode mit JDBC-Driver:
<a href="https://stackoverflow.com/questions/24938628/java-load-dynamically-jar">Stack overflow</a>
```java
public static void main(String[] args) {
        URL url;
        try {
            File f = new File("mysql-connector-java-5.1.22.jar");
            URLClassLoader child = new URLClassLoader(new URL[] { f.toURL() }, Plugin.class.getClassLoader());
            Class classToLoad = Class.forName("com.mysql.jdbc.Driver", true, child);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
```
Der Code scheint eine `ClassNotFound` Exception zu werfen.

## Methodenaufruf von geladener Class

Ausschnitt aus [Stack Overflow](https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes)

```java
/** Load and execute **/
System.out.println("Yipe");
// Create a new custom class loader, pointing to the directory that contains the compiled
// classes, this should point to the top of the package structure!
URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
// Load the class from the classloader by name....
Class<?> loadedClass = classLoader.loadClass("testcompile.HelloWorld");
// Create a new instance...
Object obj = loadedClass.newInstance();
// Santity check
if (obj instanceof DoStuff) {
    // Cast to the DoStuff interface
    DoStuff stuffToDo = (DoStuff)obj;
    // Run it baby
    stuffToDo.doStuff();
}
```

https://hevodata.com/learn/mongodb-jdbc/

```java
try {
  Class.forName("mongodb.jdbc.MongoDriver");
} catch (ClassNotFoundException e) {
  System.out.println("ERROR: Unable to load SQLServer JDBC Driver");
  e.printStackTrace();
  return;
}
```
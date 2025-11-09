import 'package:flutter/material.dart';

void main() {
  

  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      
      title: 'Lab 5 | ListView',
      theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.pinkAccent),
      ),
      home: MyHomePage(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

///For handling missing title text, use custom exception.
///Flutter has a built-in image error handler, which we will use when creating the list cards
class TitleNotFound implements Exception {
  final String message;

  TitleNotFound(this.message);

  @override
  String toString() {
    return 'TitleNotFound: $message';
  }
}

///This custom data structure class holds information about each item in the list: title, the image URL, and a desc.
///This class also handles error checking if the title text is missing
class ListViewItems{
   String title;
  String imageUrl;
  String description;

  ListViewItems(
    this.title,
  this.imageUrl,
    this.description

   
  );

  void checkItems() {
    print ("$title | $imageUrl | $description");
  }

  void checkValidTitle() {
    try {
      ProcessStringWithException(title);
    } on TitleNotFound catch (e) {
      //title text is found to be empty, error is printed and title is assigned "Placeholder"
      print ('$e');
      title = "Empty";
    }
    
  }

//Checks if text is empty os null.  If so, throw an error.
  void ProcessStringWithException(String? text) {
    if (text == null || text.isEmpty) {
    
      throw TitleNotFound("The title text is emmpty or null.");  
    }
  }


}


class _MyHomePageState extends State<MyHomePage> {

    var itemList = <ListViewItems>[];

  @override
  void initState() {
    super.initState();
    //populating up List w/ hardcoded items
  itemList = [
   ListViewItems('', 'test2', 'test3'),
   ListViewItems('', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3'),
   ListViewItems('helpme1', 'helpme2', 'helpme3')

 ];

  //Checcking if title text is empty
   for (var items in itemList) {
    items.checkValidTitle();
   }
  }
    

  @override
  Widget build(BuildContext context) {


    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Lab 5 | ListView",
        ),
      
      ),
      body:
      ListView.builder(
          itemCount: itemList.length,
          itemBuilder: (context, position) {
            return Card(
              elevation: 2.0,
              child: Padding(
                padding: const EdgeInsets.all(20.0),
                child: Row(
                  mainAxisSize:MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.start,
              
                  children: <Widget> [
                   ///IMAGE -> TEXT
                    Image.network(
                       
                        
                        itemList[position].imageUrl,
                        
                        ///If the image link is broken, throw placeholder
                        errorBuilder: (context, error, stackTrace) {
                                  return Icon(
                            Icons.broken_image,
                           size: 100,
                           color: Colors.grey,
                                 );
                        },
                        
                       height:240,
                       fit: BoxFit.cover
                       ),
                     
                  Column (children: [
                     Text( //Set up title
                        itemList[position].title,
                       style: TextStyle(fontSize: 30.0, fontWeight: FontWeight.bold, color:Theme.of(context).colorScheme.inverseSurface),
                     ),
                    SizedBox(height: 8.0),
                    Text (
                         itemList[position].description,
                       style: TextStyle(fontSize: 22.0),
                    ),
                  ],)
                  
                  ],
                ),
              ),
            );
          },
        ), // use the below ListView codes here to gain a better understanding of ListView.
    );
  }
}
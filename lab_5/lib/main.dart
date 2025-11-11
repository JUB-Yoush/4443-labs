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
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.pinkAccent,
        ), //colour theme established with seed (makes a colour pallet using a selected colour with proximities)
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
class ListViewItems {
  String title;
  String imageUrl;
  String description;

  ListViewItems(this.title, this.imageUrl, this.description);

  void checkItems() {
    print("$title | $imageUrl | $description");
  }

  void checkValidTitle() {
    try {
      ProcessStringWithException(title);
    } on TitleNotFound catch (e) {
      //title text is found to be empty, error is printed and title is assigned "Placeholder"
      print('$e');
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
      ListViewItems('', 'test2', ''),
      ListViewItems(
        '',
        'test2',
        'List item with no title nor valid image link.',
      ),
      ListViewItems(
        'Chocolate Chip Cookie',
        'https://assets.bonappetit.com/photos/5ca534485e96521ff23b382b/1:1/w_2560%2Cc_limit/chocolate-chip-cookie.jpg',
        'A classic cookie.  Very yummy.  10/10.',
      ),
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
    ];

    //Checcking if title text is empty
    for (var items in itemList) {
      items.checkValidTitle();
    }
  }

  ///Loads main screen with list of items
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Lab 5 | ListView")),
      body: ListView.builder(
        itemCount: itemList.length,
        itemBuilder: (context, position) {
          ///we refactored our list node, so it generates by passing the list items
          return ItemCard(itemList: itemList, position: position);
        },
      ), // use the below ListView codes here to gain a better understanding of ListView.
    );
  }
}

class ItemCard extends StatelessWidget {
  const ItemCard({super.key, required this.itemList, required this.position});

  ///This widget uses the ItemList data that was instantiated in _MyHomePageState, and the position, which is assigned individually to each card
  final List<ListViewItems> itemList;
  final int position;

  ///helper function that changes the text size based on screen dimension
  double getResponsiveFontSize(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    return screenWidth * 0.04;
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2.0,

      ///Inkwell produces button functioanlity and animations when clicking the button.  I
      child: InkWell(
        splashColor: Theme.of(context)
            .colorScheme
            .inversePrimary, // button changes colour when we click it using
        //the exiting theme, but inverse
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute<void>(
              builder: (context) => Details(
                itemList: itemList,
                position: position,
              ), //moves to edtailed view when pressed and passes select information to populate detailed view
            ),
          );
        },
        child: Padding(
          padding: const EdgeInsets.all(20.0),

          child: Row(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,

            spacing: 10,
            children: <Widget>[
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

                height: 100,
              ),

              Flexible(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      //Set up title
                      itemList[position].title,

                      style: TextStyle(
                        fontSize: getResponsiveFontSize(context),
                        fontWeight: FontWeight.bold,
                        color: Theme.of(context).colorScheme.inverseSurface,
                      ),
                    ),

                    Text(
                      itemList[position].description,

                      style: TextStyle(
                        fontSize: getResponsiveFontSize(context),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

///Details screen shows the picture in a larger resolution and description text
class Details extends StatelessWidget {
  const Details({super.key, required this.itemList, required this.position});

  ///The details screen will need information from the list, and the card index to access the content,
  ///which would be pass through when a card is clicked
  final List<ListViewItems> itemList;
  final int position;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      ///Assign the app bar's text to be that of the selected item's title
      appBar: AppBar(title: Text(itemList[position].title)), //app bar title based on list item title
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Align(
          alignment: AlignmentGeometry.topCenter,
          child: Column(
            mainAxisSize: MainAxisSize.min,

            children: [
              Image.network(
                itemList[position].imageUrl,

                ///If the image link is broken, throw placeholder
                errorBuilder: (context, error, stackTrace) {
                  return Icon(
                    Icons.broken_image,
                    size: 500,
                    color: Colors.grey,
                  );
                },
                height: 500,
                fit: BoxFit.cover,
              ),
              SizedBox(height: 16.0),
              Text(
                itemList[position].description,
                textAlign: TextAlign.justify,
                style: TextStyle(fontSize: 22.0),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

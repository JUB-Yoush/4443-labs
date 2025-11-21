import 'package:flutter/material.dart';
import 'package:lab_5/item_models.dart';
import 'package:lab_5/details_screen.dart';

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

import 'package:flutter/material.dart';
import 'package:lab_5/item_list.dart';
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

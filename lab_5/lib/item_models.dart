import 'package:flutter/material.dart';

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

  
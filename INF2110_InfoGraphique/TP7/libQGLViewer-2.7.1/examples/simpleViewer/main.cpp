#include "view.h"
#include "qapplication.h"

int main(int argc, char **argv) {
  // Read command lines arguments.
  QApplication application(argc, argv);

  // Instantiate the viewer.
  View view;

  view.setWindowTitle("Sphere");

  // Make the viewer window visible on screen.
  view.show();

  // Run main loop.
  return application.exec();
}

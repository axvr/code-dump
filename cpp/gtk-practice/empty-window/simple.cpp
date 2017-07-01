#include <gtkmm.h>

int main(int argc, char *argv[]) {
  auto app = Gtk::Application::create(argc, argv, "org.gtkmm.examples.base");

  Gtk::Window window;
  window.set_default_size(200, 200);

  return app->run(window);
}


// To compile run:
// g++ simple.cpp -o simple `pkg-config gtkmm-3.0 --cflags --libs`

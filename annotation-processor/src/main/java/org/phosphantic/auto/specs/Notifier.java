package org.phosphantic.auto.specs;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class Notifier {

  private final Messager messager;

  public Notifier(final Messager messager) {
    this.messager = messager;
  }

  public void writeNote(final String note) {
    messager.printMessage(Diagnostic.Kind.NOTE, note);
  }
}

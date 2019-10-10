#-------------------------------------------------
#
# Project created by QtCreator 2019-10-10T21:30:04
#
#-------------------------------------------------

QT       += core gui
QT += xml
QT += opengl
LIBS += -lglut -lGLU

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = TP4_project
TEMPLATE = app

# The following define makes your compiler emit warnings if you use
# any feature of Qt which has been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0


SOURCES += \
        main.cpp \
        mainwindow.cpp \
    simpleviewer.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/BackFaceCullingOptimizer.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/BSPSortMethod.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/EPSExporter.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Exporter.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/FIGExporter.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/gpc.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/NVector3.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/ParserGL.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Primitive.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/PrimitivePositioning.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/TopologicalSortMethod.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Vector2.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Vector3.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/VisibilityOptimizer.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/VRender.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/camera.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/constraint.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/frame.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/keyFrameInterpolator.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/manipulatedCameraFrame.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/manipulatedFrame.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/mouseGrabber.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/qglviewer.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/quaternion.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/saveSnapshot.cpp \
    ../../libQGLViewer-2.7.1/QGLViewer/vec.cpp

HEADERS += \
        mainwindow.h \
    simpleviewer.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/AxisAlignedBox.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Exporter.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/gpc.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/NVector3.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Optimizer.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/ParserGL.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Primitive.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/PrimitivePositioning.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/SortMethod.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Types.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Vector2.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/Vector3.h \
    ../../libQGLViewer-2.7.1/QGLViewer/VRender/VRender.h \
    ../../libQGLViewer-2.7.1/QGLViewer/camera.h \
    ../../libQGLViewer-2.7.1/QGLViewer/config.h \
    ../../libQGLViewer-2.7.1/QGLViewer/constraint.h \
    ../../libQGLViewer-2.7.1/QGLViewer/domUtils.h \
    ../../libQGLViewer-2.7.1/QGLViewer/frame.h \
    ../../libQGLViewer-2.7.1/QGLViewer/keyFrameInterpolator.h \
    ../../libQGLViewer-2.7.1/QGLViewer/manipulatedCameraFrame.h \
    ../../libQGLViewer-2.7.1/QGLViewer/manipulatedFrame.h \
    ../../libQGLViewer-2.7.1/QGLViewer/mouseGrabber.h \
    ../../libQGLViewer-2.7.1/QGLViewer/qglviewer.h \
    ../../libQGLViewer-2.7.1/QGLViewer/quaternion.h \
    ../../libQGLViewer-2.7.1/QGLViewer/vec.h

FORMS += \
        mainwindow.ui \
    ../../libQGLViewer-2.7.1/QGLViewer/ImageInterface.ui \
    ../../libQGLViewer-2.7.1/QGLViewer/VRenderInterface.ui

SUBDIRS += \
    ../../libQGLViewer-2.7.1/QGLViewer/QGLViewer.pro

DISTFILES += \
    ../../libQGLViewer-2.7.1/QGLViewer/qglviewer_fr.qm \
    ../../libQGLViewer-2.7.1/QGLViewer/libQGLViewer-qt5.so \
    ../../libQGLViewer-2.7.1/QGLViewer/libQGLViewer-qt5.so.2 \
    ../../libQGLViewer-2.7.1/QGLViewer/libQGLViewer-qt5.so.2.7 \
    ../../libQGLViewer-2.7.1/QGLViewer/libQGLViewer-qt5.so.2.7.1 \
    ../../libQGLViewer-2.7.1/QGLViewer/qglviewer.icns \
    ../../libQGLViewer-2.7.1/QGLViewer/qglviewer-icon.xpm \
    ../../libQGLViewer-2.7.1/QGLViewer/libQGLViewer-qt5.prl \
    ../../libQGLViewer-2.7.1/QGLViewer/qglviewer_fr.ts

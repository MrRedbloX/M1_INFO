/********************************************************************************
** Form generated from reading UI file 'viewerInterface.ui'
**
** Created by: Qt User Interface Compiler version 5.9.7
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_VIEWERINTERFACE_H
#define UI_VIEWERINTERFACE_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QCheckBox>
#include <QtWidgets/QDialog>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>
#include "interface.h"

QT_BEGIN_NAMESPACE

class Ui_Dialog
{
public:
    QVBoxLayout *vboxLayout;
    Viewer *viewer;
    QHBoxLayout *hboxLayout;
    QCheckBox *FPSCheckBox;
    QCheckBox *GridCheckBox;
    QCheckBox *AxisCheckBox;
    QSpacerItem *spacerItem;
    QPushButton *cancelButton;

    void setupUi(QDialog *Dialog)
    {
        if (Dialog->objectName().isEmpty())
            Dialog->setObjectName(QStringLiteral("Dialog"));
        Dialog->resize(650, 468);
        vboxLayout = new QVBoxLayout(Dialog);
        vboxLayout->setObjectName(QStringLiteral("vboxLayout"));
        vboxLayout->setContentsMargins(8, 8, 8, 8);
        viewer = new Viewer(Dialog);
        viewer->setObjectName(QStringLiteral("viewer"));
        QSizePolicy sizePolicy(QSizePolicy::Preferred, QSizePolicy::Preferred);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(1);
        sizePolicy.setHeightForWidth(viewer->sizePolicy().hasHeightForWidth());
        viewer->setSizePolicy(sizePolicy);

        vboxLayout->addWidget(viewer);

        hboxLayout = new QHBoxLayout();
        hboxLayout->setObjectName(QStringLiteral("hboxLayout"));
        FPSCheckBox = new QCheckBox(Dialog);
        FPSCheckBox->setObjectName(QStringLiteral("FPSCheckBox"));

        hboxLayout->addWidget(FPSCheckBox);

        GridCheckBox = new QCheckBox(Dialog);
        GridCheckBox->setObjectName(QStringLiteral("GridCheckBox"));

        hboxLayout->addWidget(GridCheckBox);

        AxisCheckBox = new QCheckBox(Dialog);
        AxisCheckBox->setObjectName(QStringLiteral("AxisCheckBox"));

        hboxLayout->addWidget(AxisCheckBox);

        spacerItem = new QSpacerItem(141, 31, QSizePolicy::Expanding, QSizePolicy::Minimum);

        hboxLayout->addItem(spacerItem);

        cancelButton = new QPushButton(Dialog);
        cancelButton->setObjectName(QStringLiteral("cancelButton"));

        hboxLayout->addWidget(cancelButton);


        vboxLayout->addLayout(hboxLayout);


        retranslateUi(Dialog);
        QObject::connect(cancelButton, SIGNAL(clicked()), Dialog, SLOT(reject()));
        QObject::connect(FPSCheckBox, SIGNAL(clicked(bool)), viewer, SLOT(setFPSIsDisplayed(bool)));
        QObject::connect(AxisCheckBox, SIGNAL(clicked(bool)), viewer, SLOT(setAxisIsDrawn(bool)));
        QObject::connect(GridCheckBox, SIGNAL(clicked(bool)), viewer, SLOT(setGridIsDrawn(bool)));
        QObject::connect(viewer, SIGNAL(gridIsDrawnChanged(bool)), GridCheckBox, SLOT(setChecked(bool)));
        QObject::connect(viewer, SIGNAL(axisIsDrawnChanged(bool)), AxisCheckBox, SLOT(setChecked(bool)));
        QObject::connect(viewer, SIGNAL(FPSIsDisplayedChanged(bool)), FPSCheckBox, SLOT(setChecked(bool)));

        QMetaObject::connectSlotsByName(Dialog);
    } // setupUi

    void retranslateUi(QDialog *Dialog)
    {
        Dialog->setWindowTitle(QApplication::translate("Dialog", "Interface", Q_NULLPTR));
        FPSCheckBox->setText(QApplication::translate("Dialog", "FPS", Q_NULLPTR));
        GridCheckBox->setText(QApplication::translate("Dialog", "Grid", Q_NULLPTR));
        AxisCheckBox->setText(QApplication::translate("Dialog", "Axis", Q_NULLPTR));
        cancelButton->setText(QApplication::translate("Dialog", "Quit", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class Dialog: public Ui_Dialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_VIEWERINTERFACE_H

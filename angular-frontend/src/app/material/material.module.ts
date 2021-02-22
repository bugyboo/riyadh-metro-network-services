import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CovalentLayoutModule } from '@covalent/core/layout';

import { FlexLayoutModule } from '@angular/flex-layout';

import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CovalentLayoutModule,
    FlexLayoutModule,
    MatListModule, MatIconModule, MatSnackBarModule, MatCardModule, MatFormFieldModule,
    MatExpansionModule, MatSelectModule, MatAutocompleteModule, MatInputModule,
    MatButtonModule, MatProgressBarModule, MatProgressSpinnerModule, MatDialogModule,
    MatMenuModule
  ],
  exports : [
    CovalentLayoutModule,
    FlexLayoutModule,
    MatListModule, MatIconModule, MatSnackBarModule, MatCardModule, MatFormFieldModule,
    MatExpansionModule, MatSelectModule, MatAutocompleteModule, MatInputModule,
    MatButtonModule, MatProgressBarModule, MatProgressSpinnerModule, MatDialogModule,
    MatMenuModule
  ]
})
export class MaterialModule { }

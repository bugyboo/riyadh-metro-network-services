import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MainComponent } from './home/main/main.component';
import { TrainsComponent } from './home/trains/trains.component';
import { UserManagerComponent } from './home/user-manager/user-manager.component';
import { LoginComponent } from './login/login.component';
import { UserGuard } from './services/user.guard';

const routes: Routes = [

  { path: '', component: HomeComponent, children: [
    { path: '', component: MainComponent }
  ]},

  { path: 'login', component: LoginComponent },

  { path: 'trains', component: HomeComponent, canActivate: [UserGuard], children: [
    { path: '', component: TrainsComponent }
  ]},

  { path: 'users', component: HomeComponent, canActivate: [UserGuard], children: [
    { path: '', component: UserManagerComponent }
  ]},

  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

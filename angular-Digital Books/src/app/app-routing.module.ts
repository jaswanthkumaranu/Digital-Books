import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-author/board-moderator.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardReaderComponent } from './board-reader/board-reader.component';
import { BookContentComponent } from './book-content/book-content.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'author', component: BoardModeratorComponent },
  { path: 'reader', component: BoardReaderComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'read', component: BookContentComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

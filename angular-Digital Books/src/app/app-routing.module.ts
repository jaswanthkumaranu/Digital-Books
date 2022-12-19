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
import { UpdateBookComponent } from './update-book/update-book.component';
import { EditbookComponent } from './editbook/editbook.component';
import { ErrorPageComponentComponent } from './error-page-component/error-page-component.component';

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
  { path: 'updateBook', component: UpdateBookComponent },
  { path: 'editBook', component: EditbookComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**',pathMatch: 'full', component: ErrorPageComponentComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

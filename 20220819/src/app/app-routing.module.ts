import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'user',
    loadChildren: () => import('./all-users/all-users.module').then(m => m.AllUsersModule)
  },
  {
    path: 'user/create',
    loadChildren: () => import('./create/create.module').then(m => m.CreateModule)
  },
  {
    path: 'user/update',
    loadChildren: () => import('./update/update.module').then(m => m.UpdateModule)
  },
  {
    path: 'user/:id',
    loadChildren: () => import('./user/user.module').then(m => m.UserModule)
  },
  {
    path: '**',
    redirectTo: 'user',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules,
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }

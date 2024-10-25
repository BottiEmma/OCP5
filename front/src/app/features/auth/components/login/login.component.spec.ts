import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {of, throwError} from "rxjs";
import {SessionInformation} from "src/app/interfaces/sessionInformation.interface";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let sessionService: SessionService;
  let router: Router;
  let authService: AuthService;

  beforeEach(async () => {
    sessionService = { logIn: jest.fn() } as any;
    router = { navigate: jest.fn() } as any;
    authService = { login: jest.fn() } as any;
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        {provide: sessionService, useValue: SessionService},
        {provide: Router, useValue: router},
        {provide: AuthService, useValue: authService},
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not log in if email and password are missing', () => {
    component.form.setValue({email: '', password: ''});
    expect(component.form.invalid).toBeTruthy();
  });

  it('should not log in if the email and password are wrong and return error', () => {
    // spy sur la fonction login de AuthService et retourne une erreur
    const loginSpy = jest.spyOn(authService, 'login').mockReturnValue(throwError(() => new Error('Wrong email or password')));
    // Met des mauvais identifiants dans le formulaire
    component.form.setValue({email: 'no@test.com', password: 'badPassword'});
    // Envoie le formulaire
    component.submit();
    fixture.detectChanges();
    // vérifie que l'erreur est bien là
    expect(component.onError).toBeTruthy();
    expect(loginSpy).toHaveBeenCalledWith({email: 'no@test.com', password: 'badPassword'});
  });

  it('should log in and redirect to /sessions', () => {
    // Mock de la session
    const sessionInformation: SessionInformation = {token: 'token', type: 'type', id: 1, username: 'username', firstName: 'firstname', lastName: 'lastname', admin: true};
    jest.spyOn(authService, 'login').mockReturnValue(of(sessionInformation));
    const loginSpy = jest.spyOn(authService, 'login').mockReturnValue(of(sessionInformation));
    const logInSpy = jest.spyOn(sessionService, 'logIn');
    // Bons identifiants dans le formulaire
    component.form.setValue({email: 'yoga@studio.com', password: 'test!1234'});
    component.submit();
    fixture.detectChanges();

    expect(loginSpy).toHaveBeenCalled();
    expect(logInSpy).toHaveBeenCalled(); // First check if it was called at all
    expect(logInSpy).toHaveBeenCalledWith(sessionInformation);
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  });
});

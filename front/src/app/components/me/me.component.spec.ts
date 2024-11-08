import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';

import { MeComponent } from './me.component';
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/user.interface";
import {of} from "rxjs";

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let sessionService: SessionService;
  let router: Router;
  let matSnackBar: MatSnackBar;
  let userService: UserService;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    },
    logOut: jest.fn()
  }
  beforeEach(async () => {
    router = { navigate: jest.fn() } as any;
    matSnackBar = { open: jest.fn() } as any;
    userService = { getById: jest.fn().mockReturnValue(of({})), delete: jest.fn().mockReturnValue(of({})) } as any;
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: Router, useValue: router },
        { provide: MatSnackBar, useValue: matSnackBar },
        { provide: UserService, useValue: userService },
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    sessionService = TestBed.inject(SessionService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get user informations', () => {
    const user: User = {id: 1, email: 'test@mail.com', lastName: 'lastName', firstName: 'firstName', admin: false, password: 'password', createdAt: new Date(), updatedAt: new Date()};
    jest.spyOn(userService, 'getById').mockReturnValue(of(user));
    component.ngOnInit();
    fixture.detectChanges();
    expect(userService.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(user);
  });

  it('should go back', () => {
    const backSpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(backSpy).toHaveBeenCalled();
  });

  it('should delete user', () => {
    jest.spyOn(userService, 'delete').mockReturnValue(of({}));
    const logOutSpy = jest.spyOn(sessionService, 'logOut');
    component.delete();
    fixture.detectChanges();
    expect(userService.delete).toHaveBeenCalledWith('1');
    expect(matSnackBar.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
    expect(logOutSpy).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/']);
  });
});

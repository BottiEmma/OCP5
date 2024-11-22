import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import {SessionApiService} from "../../services/session-api.service";
import {TeacherService} from "../../../../services/teacher.service";
import {ActivatedRoute, Router} from "@angular/router";
import {of} from "rxjs";
import {Session} from "../../interfaces/session.interface";
import {Teacher} from "../../../../interfaces/teacher.interface";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {By} from "@angular/platform-browser";
import { DebugElement } from '@angular/core';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let route: ActivatedRoute;
  let service: SessionService;
  let sessionApiService: SessionApiService;
  let teacherService: TeacherService;
  let matSnackBar: MatSnackBar;
  let router: Router;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }
  const mockSession: Session = {
    id: 1,
    name: 'name',
    description: 'description',
    date: new Date(),
    teacher_id: 1,
    users: [1],
    createdAt: new Date(),
    updatedAt: new Date()
  };

  const mockTeacher: Teacher = {
    id: 1,
    lastName: 'lastName',
    firstName: 'firstName',
    createdAt: new Date(),
    updatedAt: new Date()
  }


  beforeEach(async () => {
    route = { snapshot: { paramMap: { get: jest.fn().mockReturnValue('1'), has: jest.fn().mockReturnValue('1'),  getAll: jest.fn().mockReturnValue('1'), keys: jest.fn() } } } as any;
    matSnackBar = { open: jest.fn() } as any;
    sessionApiService = { detail: jest.fn().mockReturnValue(of(mockSession)), create: jest.fn(), update: jest.fn(), participate: jest.fn().mockReturnValue(of({})), unParticipate: jest.fn().mockReturnValue(of({})), delete: jest.fn().mockReturnValue(of({})) } as any;
    service = { sessionInformation: mockSessionService } as any;
    teacherService = { all: jest.fn().mockReturnValue(of(mockTeacher)), detail: jest.fn().mockReturnValue(of(mockTeacher))} as any;
    router = { navigate: jest.fn(), url: '' } as any;
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        MatCardModule,
        MatIconModule,
        ReactiveFormsModule
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: ActivatedRoute, useValue: route},
        { provide: MatSnackBar, useValue: matSnackBar },
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: sessionApiService },
        { provide: TeacherService, useValue: teacherService },
        { provide: Router, useValue: router },
      ],
    })
      .compileComponents();
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Integration test
  it('should initialize session and teacher on ngOnInit', () => {
    component.ngOnInit();
    fixture.detectChanges();

    expect(component.session).toEqual(mockSession);
    expect(component.teacher).toEqual(mockTeacher);
    expect(component.isParticipate).toBeTruthy();
    expect(teacherService.detail).toHaveBeenCalledWith(mockSession.teacher_id.toString());
  });

  it('should go back to previous page', () => {
    const backSpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(backSpy).toHaveBeenCalled();
  });

  it('should participate in session', () => {
    component.participate();
    fixture.detectChanges();

    expect(sessionApiService.participate).toHaveBeenCalledWith(mockSession.id?.toString(), mockSessionService.sessionInformation.id.toString());
  });

  it('should unparticipate to the session', () => {
    component.unParticipate();
    fixture.detectChanges();

    expect(sessionApiService.unParticipate).toHaveBeenCalledWith(mockSession.id?.toString(), mockSessionService.sessionInformation.id.toString());
  });

  it('should not display the delete button if the user is not admin', () => {
    component.isAdmin = false;
    fixture.detectChanges();

    const deleteButton = fixture.debugElement.queryAll(By.css('button')).find((button) =>
      button.nativeElement.querySelector('mat-icon')?.textContent.trim() === 'delete'
    );
    expect(deleteButton).toBeFalsy();
  });

  it('should display the participate button', () => {
    component.isAdmin = false;
    component.isParticipate = true;
    fixture.detectChanges();

    const participateButton = fixture.debugElement.queryAll(By.css('button')).find((button) =>
      button.nativeElement.querySelector('mat-icon')?.textContent.trim() === 'person_remove'
    );
    expect(participateButton).toBeTruthy();
  });

  it('should display the unparticipate button', () => {
    component.isAdmin = false;
    component.isParticipate = false;
    fixture.detectChanges();

    const participateButton = fixture.debugElement.queryAll(By.css('button')).find((button) =>
      button.nativeElement.querySelector('mat-icon')?.textContent.trim() === 'person_add'
    );
    expect(participateButton).toBeTruthy();
  });

  it('should delete the session and navigate to sessions', () => {
    component.delete();
    fixture.detectChanges();
    expect(sessionApiService.delete).toHaveBeenCalledWith('1');
    expect(matSnackBar.open).toHaveBeenCalledWith(
      'Session deleted !',
      'Close',
      { duration: 3000 }
    );
    expect(router.navigate).toHaveBeenCalledWith(['sessions']);
  });

});


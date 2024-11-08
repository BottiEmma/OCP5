import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TeacherService } from './teacher.service';
import {Teacher} from "../interfaces/teacher.interface";

describe('TeacherService', () => {
  let service: TeacherService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule, HttpClientTestingModule
      ],
      providers: [TeacherService]
    });
    service = TestBed.inject(TeacherService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all teachers', () => {
    const mockTeachers: Teacher[] = [
      { id: 1, firstName: 'firstName1', lastName: 'lastName1', createdAt: new Date(), updatedAt: new Date()},
      { id: 2, firstName: 'firstName2', lastName: 'firstName2', createdAt: new Date(), updatedAt: new Date() },
    ];

    service.all().subscribe((teachers) => {
      expect(teachers).toEqual(mockTeachers);
    });
    const req = httpTestingController.expectOne('api/teacher');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeachers);
  });

  it('should fetch teacher details by id', () => {
    const mockTeacher: Teacher = { id: 1, firstName: 'firstName1', lastName: 'lastName1', createdAt: new Date(), updatedAt: new Date() };

    service.detail('1').subscribe((teacher) => {
      expect(teacher).toEqual(mockTeacher);
    });

    const req = httpTestingController.expectOne('api/teacher/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeacher);
  });
});

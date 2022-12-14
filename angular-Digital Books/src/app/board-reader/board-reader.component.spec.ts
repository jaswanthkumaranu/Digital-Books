import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardReaderComponent } from './board-reader.component';

describe('BoardReaderComponent', () => {
  let component: BoardReaderComponent;
  let fixture: ComponentFixture<BoardReaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardReaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoardReaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

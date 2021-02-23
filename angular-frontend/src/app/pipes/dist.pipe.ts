import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dist'
})
export class DistPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {

    if (value) {

      let val: number = value;

      if (( val / 1000) > 2 ) {
        val = val / 1000;
        return `${val.toFixed(2)} KM`;
      }

      return `${val} Meters`;
    }
    return '';
  }

}

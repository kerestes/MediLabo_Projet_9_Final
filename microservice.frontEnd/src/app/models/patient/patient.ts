import { Adresse } from "../adresse/adresse"

export class Patient {
  private _id:number|undefined

  constructor(
    public prenom?:string,
    public nom?:string,
    public dateNaissance?:number,
    public genre?:string,
    public adresse?:Adresse,
    public telephone?:string,
    id?:number){
      this._id = id;
  }

  get id(){
    return this._id;
  }

}

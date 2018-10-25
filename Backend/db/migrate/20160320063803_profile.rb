class Profile < ActiveRecord::Migration
  def change
    create_table :profiles do |t|
      t.string :name, null: false, default: ''
      t.string :phone, null: false, default: ''
      t.string :email, null: false, default: ''
    end
  end
end
